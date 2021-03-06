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
    }

    /**
     * 확진자와 사망자 기록을 업데이트 합니다.
     *
     * <p>오늘자 데이터가 없을 경우 업데이트에 실패할 수 있습니다. 이 경우 어제의 데이터를 놔두게 됩니다.</p>
     */
    public void update() {
        CoronaEntity data = getData();
        if (data != null && LocalDateTimeUtil.isToday(data.getUpdateTime())) {
            log.info("Corona data already updated: " + data.getUpdateTime());
            return;
        }

        log.info("Corona data updated called");
        String key = PropertiesUtil.loadFile("key").getProperty("corona.token");
        String startDate = LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String endDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson"
                + "?serviceKey=" + key + "&startCreateDt=" + startDate + "&endCreateDt=" + endDate;

        try (APIConnector connector = new APIConnector()) {
            Document document = XmlUtil.parse(connector.getResult(url, ContentType.XML));

            String resultCode = document.getRootElement().element("header").element("resultCode").getText();
            APICode apiCode = APICode.findByCode(resultCode);
            if (apiCode != APICode.NORMAL_SERVICE)
                throw new IllegalArgumentException("공공 API 와 연동하는데 실패했습니다: " + (apiCode != null ? apiCode.getDisplay() : null));

            List<Element> items = document.getRootElement().element("body").element("items").elements();

            if (items.size() == 2) {
                Element startItem, endItem;

                if (items.get(0).element("stateDt").getText().equals(startDate)) {
                    startItem = items.get(0);
                    endItem = items.get(1);
                } else {
                    startItem = items.get(1);
                    endItem = items.get(0);
                }

                long startDeathCnt = Long.parseLong(startItem.element("deathCnt").getText());
                long startDecideCnt = Long.parseLong(startItem.element("decideCnt").getText());

                long endDeathCnt = Long.parseLong(endItem.element("deathCnt").getText());
                long endDecideCnt = Long.parseLong(endItem.element("decideCnt").getText());

                long addDeath = endDeathCnt - startDeathCnt;
                long addDecide = endDecideCnt - startDecideCnt;

                LocalDateTime updateTime = LocalDateTime.now();

                dao.open();
                dao.deleteAll();
                dao.saveAllAndClose(Collections.singleton(new CoronaEntity(endDeathCnt, endDecideCnt, addDeath, addDecide, updateTime)));
            } else {
                log.warn("Failed to get Today's COVID-19 data");
            }
        } catch (IOException | DocumentException | RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update COVID-19 data");
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
