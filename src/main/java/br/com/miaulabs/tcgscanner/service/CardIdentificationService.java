package br.com.miaulabs.tcgscanner.service;

import org.opencv.core.*;
import org.opencv.features2d.BFMatcher;
import org.opencv.features2d.ORB;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CardIdentificationService {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Carregar a biblioteca nativa do OpenCV
    }

    // Carregar o conjunto de imagens das cartas do diretório resources/cards
    public Map<String, Mat> loadSetImages(String setFolder) throws IOException {
        Map<String, Mat> imgDict = new HashMap<>();

        // Acessa o diretório de cartas no classpath
        File folder = new File(getClass().getClassLoader().getResource(setFolder).getFile());
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg"));

        ORB orb = ORB.create();
        for (File file : files) {
            Mat img = Imgcodecs.imread(file.getAbsolutePath());
            Mat gray = new Mat();
            Imgproc.cvtColor(img, gray, Imgproc.COLOR_BGR2GRAY);
            MatOfKeyPoint keypoints = new MatOfKeyPoint();
            Mat descriptors = new Mat();

            orb.detectAndCompute(gray, new Mat(), keypoints, descriptors);
            imgDict.put(file.getName(), descriptors);
        }

        return imgDict;
    }

    // Comparar a carta com o conjunto e encontrar a correspondência
    public String compareCardWithSet(String cardImagePath, String setFolder) throws IOException {
        Map<String, Mat> imgDict = loadSetImages(setFolder);

        // Carregar a imagem da carta a ser comparada
        Mat cardImage = Imgcodecs.imread(cardImagePath);
        Mat grayCardImage = new Mat();
        Imgproc.cvtColor(cardImage, grayCardImage, Imgproc.COLOR_BGR2GRAY);

        // Detectar pontos de interesse e descritores para a carta de entrada
        ORB orb = ORB.create();
        MatOfKeyPoint keypointsCard = new MatOfKeyPoint();
        Mat descriptorsCard = new Mat();
        orb.detectAndCompute(grayCardImage, new Mat(), keypointsCard, descriptorsCard);

        BFMatcher bfMatcher = new BFMatcher(Core.NORM_HAMMING, true);
        String bestMatchName = null;
        int bestMatchCount = 0;

        // Comparar com todas as imagens do conjunto
        for (Map.Entry<String, Mat> entry : imgDict.entrySet()) {
            Mat descriptorsSet = entry.getValue();

            // Comparar os descritores usando o matcher
            // Corrigido: Passar uma lista de correspondências vazia e ajustar a chamada do knnMatch
            List<MatOfDMatch> matches = new ArrayList<>();
            bfMatcher.knnMatch(descriptorsCard, descriptorsSet, matches, 2);

            // Aplicar teste de razão de Lowe para filtrar boas correspondências
            int goodMatchCount = 0;
            for (MatOfDMatch match : matches) { // Itera sobre as correspondências
                DMatch[] matchArray = match.toArray();
                if (matchArray.length >= 2) {
                    // Aplicar teste de razão para verificar boas correspondências
                    if (matchArray[0].distance < 0.75 * matchArray[1].distance) {
                        goodMatchCount++;
                    }
                }
            }

            // Verificar a melhor correspondência
            if (goodMatchCount > bestMatchCount) {
                bestMatchCount = goodMatchCount;
                bestMatchName = entry.getKey();
            }
        }

        return bestMatchName;
    }
}
