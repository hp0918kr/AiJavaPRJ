package kopo.poly.service.impl;

import kopo.poly.dto.NlpDTO;
import kopo.poly.service.INlpService;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NlpService implements INlpService {
    Komoran komoran = new Komoran(DEFAULT_MODEL.FULL); //학습용 데이터는 가장 큰 파일 사용

    @Override
    public NlpDTO getPlainText(String text) {

        log.info(this.getClass().getName() + ".getPlainText Start!");

        KomoranResult komoranResult = komoran.analyze(text);

        String result = komoranResult.getPlainText();

        NlpDTO rDTO = new NlpDTO();
        rDTO.setResult(result);

        log.info(this.getClass().getName() + ".getPainText End!");

        return rDTO;
    }

    @Override
    public NlpDTO getNouns(String text) {

        log.info(this.getClass().getName() + ".getNouns Start!");

        KomoranResult komoranResult = komoran.analyze(text);

        List<String> nouns = komoranResult.getNouns();

        NlpDTO rDTO = new NlpDTO();
        rDTO.setNouns(nouns);

        log.info(this.getClass().getName() + ".getNouns End!");
        return rDTO;
    }
}
