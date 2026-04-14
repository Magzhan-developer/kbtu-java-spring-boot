package com.timetravel.diary.dto;

import java.time.LocalDateTime;

public class TravelEvent {
    
    private String eventId;
    private Long diaryEntryId;
    private String travelerName;
    private LocalDateTime originTime;
    private LocalDateTime destinationTime;

    public TravelEvent() {
    }

    public TravelEvent(String eventId, Long diaryEntryId, String travelerName, LocalDateTime originTime, LocalDateTime destinationTime) {
        this.eventId = eventId;
        this.diaryEntryId = diaryEntryId;
        this.travelerName = travelerName;
        this.originTime = originTime;
        this.destinationTime = destinationTime;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Long getDiaryEntryId() {
        return diaryEntryId;
    }

    public void setDiaryEntryId(Long diaryEntryId) {
        this.diaryEntryId = diaryEntryId;
    }

    public String getTravelerName() {
        return travelerName;
    }

    public void setTravelerName(String travelerName) {
        this.travelerName = travelerName;
    }

    public LocalDateTime getOriginTime() {
        return originTime;
    }

    public void setOriginTime(LocalDateTime originTime) {
        this.originTime = originTime;
    }

    public LocalDateTime getDestinationTime() {
        return destinationTime;
    }

    public void setDestinationTime(LocalDateTime destinationTime) {
        this.destinationTime = destinationTime;
    }

    @Override
    public String toString() {
        return "TravelEvent{" +
                "eventId='" + eventId + '\'' +
                ", diaryEntryId=" + diaryEntryId +
                ", travelerName='" + travelerName + '\'' +
                ", originTime=" + originTime +
                ", destinationTime=" + destinationTime +
                '}';
    }
}
