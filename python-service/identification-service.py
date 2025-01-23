import os
import cv2
import numpy as np
from flask import Flask, request, jsonify
from werkzeug.utils import secure_filename
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from webdriver_manager.chrome import ChromeDriverManager

app = Flask(__name__)

# Função para carregar o conjunto de imagens
def load_set_images(set_folder):
    img_dict = {}
    named_dict = {}
    orb = cv2.ORB_create()

    for file in os.listdir(set_folder):
        if file.endswith((".png", ".jpg", ".jpeg")):
            file_path = os.path.join(set_folder, file)
            img = cv2.imread(file_path)
            img_gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
            keypoints, descriptors = orb.detectAndCompute(img_gray, None)
            img_dict[file] = {"image": img, "keypoints": keypoints, "descriptors": descriptors}
            named_dict[file] = file.split(".")[0]

    return img_dict, named_dict, orb

# Função para comparar a imagem da carta com o conjunto
def compare_card_with_set(card_image_path, set_folder):
    img_dict, named_dict, orb = load_set_images(set_folder)

    # Carregar a imagem de entrada
    card_image = cv2.imread(card_image_path)
    if card_image is None:
        raise ValueError("Não foi possível carregar a imagem da carta.")

    card_image_gray = cv2.cvtColor(card_image, cv2.COLOR_BGR2GRAY)
    keypoints_r, descriptors_r = orb.detectAndCompute(card_image_gray, None)

    # Matcher de características
    bf = cv2.BFMatcher(cv2.NORM_HAMMING)
    best_match_name = None
    best_match_count = 0

    for name, data in img_dict.items():
        keypoints_s = data["keypoints"]
        descriptors_s = data["descriptors"]
        raw_matches = bf.knnMatch(descriptors_r, descriptors_s, k=2)

        good_matches = []
        for m, n in raw_matches:
            if m.distance < 0.75 * n.distance:
                good_matches.append(m)

        if len(good_matches) > best_match_count:
            best_match_count = len(good_matches)
            best_match_name = named_dict[name]

    return best_match_name

# Função para fazer o scraping no LigaMagic
def scrape_card_details(card_name):
    options = Options()
    options.add_argument("--headless")
    options.add_argument("--disable-gpu")
    options.add_argument("--no-sandbox")
    driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=options)

    card_details = {}
    try:
        base_url = "https://www.ligamagic.com.br/?view=cards/card&card="
        driver.get(base_url + card_name.replace("-", "%20"))

        # Buscar imagem
        image_element = driver.find_element(By.ID, "featuredImage")
        card_details["image_url"] = image_element.get_attribute("src")

        # Buscar preços
        price_container = driver.find_element(By.ID, "container-show-price")
        prices = price_container.find_elements(By.CLASS_NAME, "container-price-mkp")
        price_details = {}
        for price in prices:
            card_type = price.find_element(By.CLASS_NAME, "extras").text
            min_price = price.find_element(By.CSS_SELECTOR, ".min .price").text
            avg_price = price.find_element(By.CSS_SELECTOR, ".medium .price").text
            max_price = price.find_element(By.CSS_SELECTOR, ".max .price").text
            price_details[card_type] = {
                "min": min_price,
                "avg": avg_price,
                "max": max_price,
            }
        card_details["prices"] = price_details

        # Buscar raridade
        rarity_element = driver.find_element(By.ID, "details-screen-rarity")
        card_details["rarity"] = rarity_element.text if rarity_element else None

        # Localizar o elemento pai que contém o texto "Tipo"
        type_parent = driver.find_element(By.XPATH, "//div[contains(@class, 'container-details') and .//span[contains(text(), 'Tipo')]]")

        # Buscar os elementos <a> dentro do pai
        type_elements = type_parent.find_elements(By.TAG_NAME, "a")

        # Extrair os textos
        types = [element.text for element in type_elements]
        card_details["type"] = ", ".join(types) if types else None
    except Exception as e:
        card_details["type"] = None
        card_details["type_error"] = str(e)
    finally:
        driver.quit()

    return card_details

# Endpoint principal
@app.route('/identify_card', methods=['POST'])
def identify_card():
    if 'image' not in request.files:
        return jsonify({'error': 'No file part'}), 400

    image_file = request.files['image']
    if image_file.filename == '':
        return jsonify({'error': 'No selected file'}), 400

    # Salvar imagem
    filename = secure_filename(image_file.filename)
    image_path = os.path.join("uploads", filename)
    image_file.save(image_path)

    set_folder = "set"
    try:
        card_name = compare_card_with_set(image_path, set_folder)
        if card_name:
            card_name_clean = "-".join(card_name.split("-")[2:])  # Extrair apenas o nome
            card_details = scrape_card_details(card_name_clean)
            return jsonify({"card_name": card_name_clean, "details": card_details}), 200
        else:
            return jsonify({'error': 'Card not found'}), 404
    except Exception as e:
        return jsonify({'error': str(e)}), 500

app.config['MAX_CONTENT_LENGTH'] = 16 * 1024 * 1024  # Limite de 16 MB para upload

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
