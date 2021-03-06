package com.encens.khipus.dashboard.module.cashbox;

import com.encens.khipus.dashboard.component.factory.DashboardObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @version 2.7
 */
public class DebtExtended implements DashboardObject {
    private String facultyCode;
    private String facultyName;

    private DebtExtendedAttribute deposit = new DebtExtendedAttribute();

    private DebtExtendedAttribute admissionRight = new DebtExtendedAttribute();

    private DebtExtendedAttribute computer = new DebtExtendedAttribute();

    private DebtExtendedAttribute halfYear = new DebtExtendedAttribute();

    private DebtExtendedAttribute enrollment = new DebtExtendedAttribute();

    private DebtExtendedAttribute firstPay = new DebtExtendedAttribute();

    private DebtExtendedAttribute secondPay = new DebtExtendedAttribute();

    private DebtExtendedAttribute thirdPay = new DebtExtendedAttribute();

    private DebtExtendedAttribute fourthPay = new DebtExtendedAttribute();

    private DebtExtendedAttribute fifthPay = new DebtExtendedAttribute();

    private DebtExtendedAttribute additionalTopic = new DebtExtendedAttribute();

    private DebtExtendedAttribute administrationExpense = new DebtExtendedAttribute();

    private DebtExtendedAttribute firstAdministrationExpense = new DebtExtendedAttribute();

    private DebtExtendedAttribute secondAdministrationExpense = new DebtExtendedAttribute();

    private DebtExtendedAttribute thirdAdministrationExpense = new DebtExtendedAttribute();

    private DebtExtendedAttribute fourthAdministrationExpense = new DebtExtendedAttribute();

    private DebtExtendedAttribute fifthAdministrationExpense = new DebtExtendedAttribute();

    private DebtExtendedAttribute delayTopic = new DebtExtendedAttribute();

    private DebtExtendedAttribute firstPayDelayTopic = new DebtExtendedAttribute();

    private DebtExtendedAttribute secondPayDelayTopic = new DebtExtendedAttribute();

    private DebtExtendedAttribute thirdPayDelayTopic = new DebtExtendedAttribute();

    private DebtExtendedAttribute fourthPayDelayTopic = new DebtExtendedAttribute();

    private DebtExtendedAttribute fifthPayDelayTopic = new DebtExtendedAttribute();

    private DebtExtendedAttribute hospitalPractice = new DebtExtendedAttribute();

    private DebtExtendedAttribute total = new DebtExtendedAttribute();

    private List<Career> careers = new ArrayList<Career>();

    private Career currentCareer = null;

    public Object getIdentifier() {
        return facultyCode;
    }

    public String getFacultyCode() {
        return facultyCode;
    }

    public void setFacultyCode(String facultyCode) {
        this.facultyCode = facultyCode;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public DebtExtendedAttribute getDeposit() {
        return deposit;
    }

    public void setDeposit(DebtExtendedAttribute deposit) {
        this.deposit = deposit;
    }

    public DebtExtendedAttribute getAdmissionRight() {
        return admissionRight;
    }

    public void setAdmissionRight(DebtExtendedAttribute admissionRight) {
        this.admissionRight = admissionRight;
    }

    public DebtExtendedAttribute getComputer() {
        return computer;
    }

    public void setComputer(DebtExtendedAttribute computer) {
        this.computer = computer;
    }

    public DebtExtendedAttribute getHalfYear() {
        return halfYear;
    }

    public void setHalfYear(DebtExtendedAttribute halfYear) {
        this.halfYear = halfYear;
    }

    public DebtExtendedAttribute getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(DebtExtendedAttribute enrollment) {
        this.enrollment = enrollment;
    }

    public DebtExtendedAttribute getFirstPay() {
        return firstPay;
    }

    public void setFirstPay(DebtExtendedAttribute firstPay) {
        this.firstPay = firstPay;
    }

    public DebtExtendedAttribute getSecondPay() {
        return secondPay;
    }

    public void setSecondPay(DebtExtendedAttribute secondPay) {
        this.secondPay = secondPay;
    }

    public DebtExtendedAttribute getThirdPay() {
        return thirdPay;
    }

    public void setThirdPay(DebtExtendedAttribute thirdPay) {
        this.thirdPay = thirdPay;
    }

    public DebtExtendedAttribute getFourthPay() {
        return fourthPay;
    }

    public void setFourthPay(DebtExtendedAttribute fourthPay) {
        this.fourthPay = fourthPay;
    }

    public DebtExtendedAttribute getFifthPay() {
        return fifthPay;
    }

    public void setFifthPay(DebtExtendedAttribute fifthPay) {
        this.fifthPay = fifthPay;
    }

    public DebtExtendedAttribute getAdditionalTopic() {
        return additionalTopic;
    }

    public void setAdditionalTopic(DebtExtendedAttribute additionalTopic) {
        this.additionalTopic = additionalTopic;
    }

    public DebtExtendedAttribute getAdministrationExpense() {
        return administrationExpense;
    }

    public void setAdministrationExpense(DebtExtendedAttribute administrationExpense) {
        this.administrationExpense = administrationExpense;
    }

    public DebtExtendedAttribute getFirstAdministrationExpense() {
        return firstAdministrationExpense;
    }

    public void setFirstAdministrationExpense(DebtExtendedAttribute firstAdministrationExpense) {
        this.firstAdministrationExpense = firstAdministrationExpense;
    }

    public DebtExtendedAttribute getSecondAdministrationExpense() {
        return secondAdministrationExpense;
    }

    public void setSecondAdministrationExpense(DebtExtendedAttribute secondAdministrationExpense) {
        this.secondAdministrationExpense = secondAdministrationExpense;
    }

    public DebtExtendedAttribute getThirdAdministrationExpense() {
        return thirdAdministrationExpense;
    }

    public void setThirdAdministrationExpense(DebtExtendedAttribute thirdAdministrationExpense) {
        this.thirdAdministrationExpense = thirdAdministrationExpense;
    }

    public DebtExtendedAttribute getFourthAdministrationExpense() {
        return fourthAdministrationExpense;
    }

    public void setFourthAdministrationExpense(DebtExtendedAttribute fourthAdministrationExpense) {
        this.fourthAdministrationExpense = fourthAdministrationExpense;
    }

    public DebtExtendedAttribute getFifthAdministrationExpense() {
        return fifthAdministrationExpense;
    }

    public void setFifthAdministrationExpense(DebtExtendedAttribute fifthAdministrationExpense) {
        this.fifthAdministrationExpense = fifthAdministrationExpense;
    }

    public DebtExtendedAttribute getDelayTopic() {
        return delayTopic;
    }

    public void setDelayTopic(DebtExtendedAttribute delayTopic) {
        this.delayTopic = delayTopic;
    }

    public DebtExtendedAttribute getFirstPayDelayTopic() {
        return firstPayDelayTopic;
    }

    public void setFirstPayDelayTopic(DebtExtendedAttribute firstPayDelayTopic) {
        this.firstPayDelayTopic = firstPayDelayTopic;
    }

    public DebtExtendedAttribute getSecondPayDelayTopic() {
        return secondPayDelayTopic;
    }

    public void setSecondPayDelayTopic(DebtExtendedAttribute secondPayDelayTopic) {
        this.secondPayDelayTopic = secondPayDelayTopic;
    }

    public DebtExtendedAttribute getThirdPayDelayTopic() {
        return thirdPayDelayTopic;
    }

    public void setThirdPayDelayTopic(DebtExtendedAttribute thirdPayDelayTopic) {
        this.thirdPayDelayTopic = thirdPayDelayTopic;
    }

    public DebtExtendedAttribute getFourthPayDelayTopic() {
        return fourthPayDelayTopic;
    }

    public void setFourthPayDelayTopic(DebtExtendedAttribute fourthPayDelayTopic) {
        this.fourthPayDelayTopic = fourthPayDelayTopic;
    }

    public DebtExtendedAttribute getFifthPayDelayTopic() {
        return fifthPayDelayTopic;
    }

    public void setFifthPayDelayTopic(DebtExtendedAttribute fifthPayDelayTopic) {
        this.fifthPayDelayTopic = fifthPayDelayTopic;
    }

    public DebtExtendedAttribute getHospitalPractice() {
        return hospitalPractice;
    }

    public void setHospitalPractice(DebtExtendedAttribute hospitalPractice) {
        this.hospitalPractice = hospitalPractice;
    }

    public DebtExtendedAttribute getTotal() {
        return total;
    }

    public void setTotal(DebtExtendedAttribute total) {
        this.total = total;
    }

    public void addCareer(Career career) {
        currentCareer = career;
        careers.add(career);
        setSubTotalValues(career);
    }

    public List<Career> getCareers() {
        return careers;
    }

    public void setCareers(List<Career> careers) {
        this.careers = careers;
    }

    public Career getCurrentCareer() {
        return currentCareer;
    }

    private void setSubTotalValues(Career career) {
        deposit.addValues(career.getDeposit());
        admissionRight.addValues(career.getAdmissionRight());
        computer.addValues(career.getComputer());
        halfYear.addValues(career.getHalfYear());
        enrollment.addValues(career.getEnrollment());
        firstPay.addValues(career.getFifthPay());
        secondPay.addValues(career.getSecondPay());
        thirdPay.addValues(career.getThirdPay());
        fourthPay.addValues(career.getFourthPay());
        fifthPay.addValues(career.getFifthPay());
        additionalTopic.addValues(career.getAdditionalTopic());
        administrationExpense.addValues(career.getAdministrationExpense());
        firstAdministrationExpense.addValues(career.getFirstAdministrationExpense());
        secondAdministrationExpense.addValues(career.getSecondAdministrationExpense());
        thirdAdministrationExpense.addValues(career.getThirdAdministrationExpense());
        fourthAdministrationExpense.addValues(career.getFourthAdministrationExpense());
        fifthAdministrationExpense.addValues(career.getFifthAdministrationExpense());
        delayTopic.addValues(career.getDelayTopic());
        firstPayDelayTopic.addValues(career.getFirstPayDelayTopic());
        secondPayDelayTopic.addValues(career.getSecondPayDelayTopic());
        thirdPayDelayTopic.addValues(career.getThirdPayDelayTopic());
        fourthPayDelayTopic.addValues(career.getFourthPayDelayTopic());
        fifthPayDelayTopic.addValues(career.getFifthPayDelayTopic());
        hospitalPractice.addValues(career.getHospitalPractice());
        total.addValues(career.getTotal());
    }
}
