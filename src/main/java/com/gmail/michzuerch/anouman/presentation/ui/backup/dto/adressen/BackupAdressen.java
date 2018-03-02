package com.gmail.michzuerch.anouman.presentation.ui.backup.dto.adressen;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michzuerch on 25.07.15.
 */
public class BackupAdressen {
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime backupdatum;

    private List<BackupAdresse> adressen = new ArrayList<>();

    public List<BackupAdresse> getAdressen() {
        return adressen;
    }

    public void setAdressen(List<BackupAdresse> adressen) {
        this.adressen = adressen;
    }

    public LocalDateTime getBackupdatum() {
        return backupdatum;
    }

    public void setBackupdatum(LocalDateTime backupdatum) {
        this.backupdatum = backupdatum;
    }

    @Override
    public String toString() {
        return "BackupAdressen{" +
                "backupdatum=" + backupdatum +
                ", adressen=" + adressen +
                '}';
    }
}
