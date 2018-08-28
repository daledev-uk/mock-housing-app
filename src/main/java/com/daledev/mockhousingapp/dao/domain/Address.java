package com.daledev.mockhousingapp.dao.domain;

import javax.persistence.*;

/**
 * Created by Shane.Sturgeon on 06/03/2018.
 */
@Entity(name = "address")
public class Address extends AbstractEntity {

    @Column(name = "line_1", nullable = false, length = 100)
    private String line1;

    @Column(name = "line_2", length = 100)
    private String line2;

    @Column(name = "line_3", length = 100)
    private String line3;

    private String town;

    private String postcode;

    @ManyToOne(cascade = CascadeType.ALL)
    private InspectionArea inspectionArea;

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public InspectionArea getInspectionArea() {
        return inspectionArea;
    }

    public void setInspectionArea(InspectionArea inspectionArea) {
        this.inspectionArea = inspectionArea;
    }
}
