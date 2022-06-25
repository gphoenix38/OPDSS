/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package husseinm.opdss;

import java.time.LocalDate;
import java.time.Period;

/**
 * @author Rahiel
 */
public class ValidateInput {

    //Validation for names
    public static boolean validateName(String name) {
        return name.matches("[a-zA-Z]*");
    }

    //Validation for Classification Number in cataloging
    public static boolean validateDecimal(String value){
        return value.matches("\\d*(.?[0-9]+)?");
    }

    //Validations for numbers
    public static boolean validateNumber(String value){
        return value.matches("[0-9]*");
    }

    //Validation for age
    public static boolean validateAge(LocalDate localDate) {

        LocalDate l = localDate; //specify year, month, date directly
        LocalDate now = LocalDate.now();        //gets localDate
        Period diff = Period.between(l, now);    //difference between the dates is calculated


        return (diff.getYears() >= 16 && diff.getYears() < 150);
    }
    //Validation for registration date
    public static boolean validateReg(LocalDate localDate) {

        LocalDate l = localDate; //specify year, month, date directly
        LocalDate now = LocalDate.now();        //gets localDate
        Period diff = Period.between(l, now);    //difference between the dates is calculated


        return (diff.getDays()>=0);
    }

    //Validation for year of publication
    public static boolean validateYop(int year){
        LocalDate now = LocalDate.now(); //getting current date and time

        return year > now.getYear() || year < 1500;
    }
    //Validation for expiration date
    public static boolean validateExp(LocalDate localDate) {

        LocalDate l = localDate; //specify year, month, date directly
        LocalDate now = LocalDate.now();        //gets localDate
        Period diff = Period.between(l, now);    //difference between the dates is calculated


        return (diff.isNegative());
    }
    //Calculating the difference between two dates
    public static boolean calculateDiff(LocalDate localDate, LocalDate localDate1){
        Period period = Period.between(localDate, localDate1);

        return period.getDays()<=0;
    }
    //Calculating age
    public static String calculateAge(LocalDate localDate) throws NullPointerException{
        LocalDate l = localDate; //specify year, month, date directly
        LocalDate now = LocalDate.now();        //gets localDate
        Period diff = Period.between(l, now);    //difference between the dates is calculated
        if (diff.getYears() > 0)
            return String.format("Age: %d years %s", diff.getYears(), (diff.getMonths() > 0) ? diff.getMonths() + " months" : "");
        else
            return "";
    }
    //Validation for national id
    public static boolean validateNational(String national_id) {
        return national_id.matches("[a-zA-Z0-9]*");
    }

    //Validation for email address
    public static boolean validateEmail(String email) {
        return email.matches("([a-zA-Z0-9~!@#$%^&*()-_+=<>?]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+)*");
    }

    //Validation for phone number
    public static boolean validatePhone(String phone) {
        if (phone.matches("0[78]\\d{6}")) //For mobile phones and CDMA phones
            return true;
        else if (phone.matches("\\d{6}")) //For Lined phones
            return true;
        else
            return false;
    }
    //Validation for password
    public static boolean validatePassword(String password) {

        return password.matches("[a-zA-Z0-9[~!@#$%^&*()*-+<>?\"\\s]]+");
    }

    //Validation for student id
    public static boolean validateStudentID(String std_id) {
        return std_id.matches("\\d{1,2}/[0-9]*");
    }

    //Validation for confirming password
    public static boolean validateConfirmPassword(String pass1, String pass2) {
        return pass1.equals(pass2);
    }

}
