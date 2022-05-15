package net.qsef1256.capstone2022server.api.corona;

import lombok.extern.slf4j.Slf4j;
import net.qsef1256.capstone2022server.api.APIConnector;
import net.qsef1256.capstone2022server.api.enums.APICode;
import net.qsef1256.capstone2022server.api.enums.ContentType;
import net.qsef1256.capstone2022server.database.DaoCommonJpaImpl;
import net.qsef1256.capstone2022server.schedule.DiaScheduler;
import net.qsef1256.capstone2022server.util.LocalDateTimeUtil;
import net.qsef1256.capstone2022server.util.PropertiesUtil;
import net.qsef1256.capstone2022server.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Slf4j
public class CoronaAPI {

    public static DaoCommonJpaImpl<CoronaEntity, Long> dao = new DaoCommonJpaImpl<>(CoronaEntity.class);

    static {
        new CoronaAPI().update();
        DiaScheduler.executePerDay(() -> new CoronaAPI().update());
    }

    /**
     * 확진자와 사망자 기록을 업데이트 합니다.
     */
    public void update() {
        CoronaEntity data = getData();
        if (data != null && LocalDateTimeUtil.isToday(data.getUpdateTime())) {
            log.info("Corona data already updated: " + data.getUpdateTime());
            return;
        }

        System.out.println("Corona data updated called");
        String key = PropertiesUtil.loadFile("key").getProperty("corona.token");
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson"
                + "?serviceKey=" + key + "&startCreateDt=" + date;

        try (APIConnector connector = new APIConnector()) {
            Document document = XmlUtil.parse(connector.getResult(url, ContentType.XML));

            String resultCode = document.getRootElement().element("header").element("resultCode").getText();
            APICode apiCode = APICode.findByCode(resultCode);
            if (apiCode != APICode.NORMAL_SERVICE)
                throw new IllegalArgumentException("공공 API 와 연동하는데 실패했습니다: " + (apiCode != null ? apiCode.getDisplay() : null));

            Element item = document.getRootElement()
                    .element("body").element("items").elements().get(0);
            long deathCnt = Long.parseLong(item.element("deathCnt").getText());
            long decideCnt = Long.parseLong(item.element("decideCnt").getText());
            LocalDateTime updateTime = LocalDateTime.now();

            dao.open();
            dao.deleteAll();
            dao.saveAllAndClose(Collections.singleton(new CoronaEntity(deathCnt, decideCnt, updateTime)));
        } catch (IOException | DocumentException | RuntimeException e) {
            e.printStackTrace();
        }

    }

    @Nullable
    public static CoronaEntity getData() {
        dao.open();
        List<CoronaEntity> data = dao.findAll();
        CoronaEntity coronaEntity = data.size() != 0 ? data.get(0) : null;

        dao.close();
        return coronaEntity;
    }

    public static void main(String[] args) {
        new CoronaAPI().update();
        System.out.println(CoronaAPI.getData());
    }

}
