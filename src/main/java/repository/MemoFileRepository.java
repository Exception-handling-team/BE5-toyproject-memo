package repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import app.Memo;
import global.AppConfig;
import standard.Util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class MemoFileRepository {
    private static final String DB_PATH = AppConfig.getDbPath() + "/";
    private static final String LASTID_FILE_PATH = DB_PATH + "lastid.txt";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MemoFileRepository() {
        Init();
    }

    public void Init() {
        if (!Util.File.exists(LASTID_FILE_PATH)) {
            Util.File.createFile(LASTID_FILE_PATH);
        }

        if (!Util.File.exists(DB_PATH)) {
            Util.File.createDir(DB_PATH);
        }
    }

    public Memo save(Memo memo) {
        boolean isNew = memo.isNew();

        if (isNew) {
            memo.setId(getLastId() + 1);
        }

        try {
            String json = objectMapper.writeValueAsString(memo);
            String filePath = getFilePath(memo.getId());

            Util.File.write(filePath, json);

            if (isNew) {
                Util.File.write(LASTID_FILE_PATH, String.valueOf(memo.getId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return memo;
    }

    public int getLastId() {
        String idStr = Util.File.readAsString(LASTID_FILE_PATH);

        if (idStr.isEmpty()) {
            return 0;
        }

        try {
            return Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public List<Memo> findAll() {
        ObjectMapper objectMapper = new ObjectMapper();

        return Util.File.getPaths(DB_PATH).stream()
                .map(Path::toString)
                .filter(path -> path.endsWith(".json"))
                .map(path -> {
                    try {
                        String jsonContent = Util.File.readAsString(path);
                        return objectMapper.readValue(jsonContent, Memo.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

    public void deleteById(int id) {
        if (!isFileExists(id)) {
            System.out.println("잘못된 id입니다: " + id);
            return;
        }

        String filePath = getFilePath(id);
        Util.File.delete(filePath);
    }

    private boolean isFileExists(int id) {
        String filePath = getFilePath(id);
        return Util.File.exists(filePath);
    }

    public Memo findById(int id) {
        if (!isFileExists(id)) {
            System.out.println("잘못된 id입니다: " + id);
            return null;
        }

        String filePath = getFilePath(id);

        try {
            String jsonContent = Util.File.readAsString(filePath);
            return objectMapper.readValue(jsonContent, Memo.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 새로 추가된 메서드: 파일 경로 생성
    private String getFilePath(int id) {
        return DB_PATH + id + ".json";
    }
}
