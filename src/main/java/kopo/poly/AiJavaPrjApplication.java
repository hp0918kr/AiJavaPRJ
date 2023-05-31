package kopo.poly;

import kopo.poly.dto.NlpDTO;
import kopo.poly.dto.OcrDTO;
import kopo.poly.service.INlpService;
import kopo.poly.service.IOcrService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class AiJavaPrjApplication implements CommandLineRunner {
    //@Service 정의된 자바 파일
    //Spring Frameworks 실행될 때, @Service 정의한 자바는 자동으로 메모리에 올림
    //메모라에 올라간 OcrService 객체를 ocrService 변수에 객체를 넣어주기
    private final IOcrService ocrService; //이미지 인식
    private final INlpService nlpService; //자연어 처리

    public static void main(String[] args) {
        SpringApplication.run(AiJavaPrjApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {

        log.info("자바 프로그램 시작!!");

        String filePath = "image"; // 문자열을 인식할 이미지 파일 경로
        String fileName = "image01.png"; //문자열을 인식할 이미지 파일 이름

        //전달할 값(Parameter) 약자로 보통 변수명 앞에 p을 붙임 => pDTO
        OcrDTO pDTO = new OcrDTO(); //OcrService의 함수에 정보를 전달할 DTO를 메모리에 올리기

        pDTO.setFilePath(filePath);
        pDTO.setFileName(fileName);

        //실행결과(result) 약자로 보통 변수며 앞에 r을 붙임 => rDTO
        OcrDTO rDTO = ocrService.getReadforImageText(pDTO);

        String result = rDTO.getResult(); // 인식된 문자열

        log.info("인식된 문자열");
        log.info(result);
        log.info("--------------------------------------------------");
        NlpDTO nlpDTO = nlpService.getPlainText(result);
        log.info("형태소별 품사 분석 결과 : " + nlpDTO.getResult());

        nlpDTO = nlpService.getNouns(result);
        List<String> nouns = nlpDTO.getNouns();

        Set<String> distinct = new HashSet<>(nouns);
        log.info("중복 제거 수행 전 단어 수 : " + nouns.size());
        log.info("중복 제거 수행 후 단어 수 : " + distinct.size());

        Map<String, Integer> rMap = new HashMap<>();

        for (String s : distinct) {
            int count = Collections.frequency(nouns, s);
            rMap.put(s, count);

            log.info(s + " : " + count);
        }
        List<Map.Entry<String, Integer>> sortResult = new LinkedList<>(rMap.entrySet());

        Collections.sort(sortResult, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        log.info("가장 많이 사용된 단어는? :" + sortResult);

        log.info("자바 프로그래밍 종료!!");

    }
}
