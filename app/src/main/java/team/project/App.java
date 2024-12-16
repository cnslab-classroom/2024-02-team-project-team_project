/*
 * This source file was generated by the Gradle 'init' task
 */
package team.project;

import team.project.datacollection.*;
import team.project.datastorage.DatabaseManager;
import team.project.analysis.GPTClient;
import team.project.analysis.OllamaClient;
import team.project.entity.Article;


import java.util.List;
import java.io.IOException;
import java.util.concurrent.*;
import java.util.Map;
import java.util.HashMap;


public class App {

    public static void main(String[] args) {
        // ExecutorService는 하나의 공유된 풀로 작업을 처리
        ExecutorService executor = Executors.newFixedThreadPool(4); // 동시에 최대 4개 스레드 실행

        Crawler naverCrawler = new Crawler();
        naverCrawler.crawl();
        List<Article> articles = naverCrawler.getArticles();
        Map<String, String> results = new HashMap<>();

        // 각 기사에 대해 비동기 작업을 생성
        List<CompletableFuture<Void>> futures = articles.stream()
            .map(article -> CompletableFuture.supplyAsync(() -> {
                OllamaClient client = new OllamaClient(executor);
                try {
                    return client.execute(article); // OllamaClient의 비동기 작업 실행
                } catch (Exception e) {
                    System.err.println("Error processing article " + article.url + ": " + e.getMessage());
                    return null;
                }
            }, executor).thenAccept(result -> {
                if (result != null) {
                    results.put(article.url, result);
                    System.out.println("\n\n종목: " + article.url + "\n- 결과: 성공" + "\n- 종목 유망성(-1 ~ 1): " + result);
                } else {
                    System.out.println("\n\n종목: " + article.url + "\n- 결과: 실패");
                }
            }))
            .toList();

        // 모든 비동기 작업 완료될 때까지 대기
        try {
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        } catch (Exception e) {
            System.out.println("전체 작업 중 에러 발생: " + e.getMessage());
        } finally {
            executor.shutdown(); // Executor 종료
        }

        // 결과 출력
        System.out.println("\n\n--- 결과 ---");
        results.forEach((url, result) -> {
            System.out.println("URL: " + url + " - 유망성: " + result);
        });

        // 분석 결과 저장
        System.out.println("\n\n--- 데이터베이스 저장 시작 ---");
        for (Article article : articles) {
            String result = results.get(article.url);
            if (result != null) {
                DatabaseManager.storeData(article, result);
            }
        }
        
        // 저장된 데이터 전체 출력
        System.out.println("\n--- 데이터베이스 조회 결과 ---");
        DatabaseManager.printAllData();

        String SearchUrl = "https://n.news.naver.com/mnews/article/014/0005282470";
        String DeleteUrl = "https://n.news.naver.com/mnews/article/014/0005282470";

        // 특정 URL 데이터 검색
        DatabaseManager.searchData(SearchUrl);

        // 특정 URL 데이터 삭제
        DatabaseManager.deleteData(DeleteUrl);

        // 모든 데이터 삭제
        DatabaseManager.deleteAllData();

    }
}
