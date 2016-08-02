package com.meiriq.xposehook.bean;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Created by tian on 16-2-2.
 */
public class SetDay implements Serializable{
    private static final String SETDAY = "set_day";

    private String day2 = "";
    private String day3 = "";
    private String day4 = "";
    private String day5 = "";
    private String day6 = "";
    private String day7 = "";
    private String day8 = "";
    private String day9 = "";
    private String day10 = "";
    private String day11 = "";
    private String day12 = "";
    private String day13 = "";
    private String day14 = "";
    private String day15 = "";
    private String day16 = "";
    private String day17 = "";
    private String day18 = "";
    private String day19 = "";
    private String day20 = "";
    private String day21 = "";
    private String day22 = "";
    private String day23 = "";
    private String day24 = "";
    private String day25 = "";
    private String day26 = "";
    private String day27 = "";
    private String day28 = "";
    private String day29 = "";
    private String day30 = "";
    private String day31 = "";

    private boolean resolution ;

    private int channel = 0;
    private int day = 0;
    private int hour = 0;
    private int hourto = 0;
    private int minute = 0;
    private int minuteto = 0;

    public SetDay() {
    }

    @Override
    public String toString() {
        return "SetDay{" +
                "day31='" + day31 + '\'' +
                ", day2='" + day2 + '\'' +
                ", day3='" + day3 + '\'' +
                ", day4='" + day4 + '\'' +
                ", day5='" + day5 + '\'' +
                ", day6='" + day6 + '\'' +
                ", day7='" + day7 + '\'' +
                ", day8='" + day8 + '\'' +
                ", day9='" + day9 + '\'' +
                ", day10='" + day10 + '\'' +
                ", day11='" + day11 + '\'' +
                ", day12='" + day12 + '\'' +
                ", day13='" + day13 + '\'' +
                ", day14='" + day14 + '\'' +
                ", day15='" + day15 + '\'' +
                ", day16='" + day16 + '\'' +
                ", day17='" + day17 + '\'' +
                ", day18='" + day18 + '\'' +
                ", day19='" + day19 + '\'' +
                ", day20='" + day20 + '\'' +
                ", day21='" + day21 + '\'' +
                ", day22='" + day22 + '\'' +
                ", day23='" + day23 + '\'' +
                ", day24='" + day24 + '\'' +
                ", day25='" + day25 + '\'' +
                ", day26='" + day26 + '\'' +
                ", day27='" + day27 + '\'' +
                ", day28='" + day28 + '\'' +
                ", day29='" + day29 + '\'' +
                ", day30='" + day30 + '\'' +
                '}';
    }

    public SetDay(String day2, String day3, String day4, String day5, String day6, String day7, String day8, String day9, String day10, String day11, String day12, String day13, String day14, String day15, String day16, String day17, String day18, String day19, String day20, String day21, String day22, String day23, String day24, String day25, String day26, String day27, String day28, String day29, String day30, String day31) {
        this.day2 = day2;
        this.day3 = day3;
        this.day4 = day4;
        this.day5 = day5;
        this.day6 = day6;
        this.day7 = day7;
        this.day8 = day8;
        this.day9 = day9;
        this.day10 = day10;
        this.day11 = day11;
        this.day12 = day12;
        this.day13 = day13;
        this.day14 = day14;
        this.day15 = day15;
        this.day16 = day16;
        this.day17 = day17;
        this.day18 = day18;
        this.day19 = day19;
        this.day20 = day20;
        this.day21 = day21;
        this.day22 = day22;
        this.day23 = day23;
        this.day24 = day24;
        this.day25 = day25;
        this.day26 = day26;
        this.day27 = day27;
        this.day28 = day28;
        this.day29 = day29;
        this.day30 = day30;
        this.day31 = day31;
    }

    public void setOnlySpinner(int channel, int day, int hour, int hourto, int minute, int minuteto){
        this.channel = channel;
        this.day = day;
        this.hour = hour;
        this.hourto = hourto;
        this.minute = minute;
        this.minuteto = minuteto;
    }

    public void setOnlyDay(SetDay onlyDay){
        this.day2 = onlyDay.getDay2();
        this.day3 = onlyDay.getDay3();
        this.day4 = onlyDay.getDay4();
        this.day5 = onlyDay.getDay5();
        this.day6 = onlyDay.getDay6();
        this.day7 = onlyDay.getDay7();
        this.day8 = onlyDay.getDay8();
        this.day9 = onlyDay.getDay9();
        this.day10 = onlyDay.getDay10();
        this.day11 = onlyDay.getDay11();
        this.day12 = onlyDay.getDay12();
        this.day13 = onlyDay.getDay13();
        this.day14 = onlyDay.getDay14();
        this.day15 = onlyDay.getDay15();
        this.day16 = onlyDay.getDay16();
        this.day17 = onlyDay.getDay17();
        this.day18 = onlyDay.getDay18();
        this.day19 = onlyDay.getDay19();
        this.day20 = onlyDay.getDay20();
        this.day21 = onlyDay.getDay21();
        this.day22 = onlyDay.getDay22();
        this.day23 = onlyDay.getDay23();
        this.day24 = onlyDay.getDay24();
        this.day25 = onlyDay.getDay25();
        this.day26 = onlyDay.getDay26();
        this.day27 = onlyDay.getDay27();
        this.day28 = onlyDay.getDay28();
        this.day29 = onlyDay.getDay29();
        this.day30 = onlyDay.getDay30();
        this.day31 = onlyDay.getDay31();
    }

    public int getDayByPosition(int position){

        String result = null;

        switch (position){

            case 2:
                result = day2;
                break;
            case 3:
                result = day3;
                break;
            case 4:
                result = day4;
                break;
            case 5:
                result = day5;
                break;
            case 6:
                result = day6;
                break;
            case 7:
                result = day7;
                break;
            case 8:
                result = day8;
                break;
            case 9:
                result = day9;
                break;
            case 10:
                result = day10;
                break;
            case 11:
                result = day11;
                break;
            case 12:
                result = day12;
                break;
            case 13:
                result = day13;
                break;
            case 14:
                result = day14;
                break;
            case 15:
                result = day15;
                break;
            case 16:
                result = day16;
                break;
            case 17:
                result = day17;
                break;
            case 18:
                result = day18;
                break;
            case 19:
                result = day19;
                break;
            case 20:
                result = day20;
                break;
            case 21:
                result = day21;
                break;
            case 22:
                result = day22;
                break;
            case 23:
                result = day23;
                break;
            case 24:
                result = day24;
                break;
            case 25:
                result = day25;
                break;
            case 26:
                result = day26;
                break;
            case 27:
                result = day27;
                break;
            case 28:
                result = day28;
                break;
            case 29:
                result = day29;
                break;
            case 30:
                result = day30;
                break;
            case 31:
                result = day31;
                break;

        }

        if(result == null)
            result = "100";

        try{
            int number = Integer.parseInt(result);
            return number;
        }catch (Exception e){
            e.printStackTrace();
        }

        return 100;
    }



    private static File getFilePath(String filePath){
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),ConfigHelper.FILE_PATH);
        if(!file.exists())
            file.mkdirs();
        file = new File(file,File.separator+filePath);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    public static SetDay loadSetDay(Context context){
        SetDay setDays = null;
        File file = getFilePath(SETDAY);
        if(file.exists()){
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(getFilePath(SETDAY)));
                setDays = (SetDay) inputStream.readObject();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        if(setDays == null){
            return new SetDay();
        }

        return setDays;
    }

    public static void saveSetDay(Context context,SetDay setDays){
        File file = getFilePath(SETDAY);
        if(file.exists()){
            file.delete();
        }
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(getFilePath(SETDAY)));
            outputStream.writeObject(setDays);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isResolution() {
        return resolution;
    }

    public void setResolution(boolean resolution) {
        this.resolution = resolution;
    }

    public String getDay2() {
        return day2;
    }

    public void setDay2(String day2) {
        this.day2 = day2;
    }

    public String getDay3() {
        return day3;
    }

    public void setDay3(String day3) {
        this.day3 = day3;
    }

    public String getDay4() {
        return day4;
    }

    public void setDay4(String day4) {
        this.day4 = day4;
    }

    public String getDay5() {
        return day5;
    }

    public void setDay5(String day5) {
        this.day5 = day5;
    }

    public String getDay6() {
        return day6;
    }

    public void setDay6(String day6) {
        this.day6 = day6;
    }

    public String getDay7() {
        return day7;
    }

    public void setDay7(String day7) {
        this.day7 = day7;
    }

    public String getDay8() {
        return day8;
    }

    public void setDay8(String day8) {
        this.day8 = day8;
    }

    public String getDay9() {
        return day9;
    }

    public void setDay9(String day9) {
        this.day9 = day9;
    }

    public String getDay10() {
        return day10;
    }

    public void setDay10(String day10) {
        this.day10 = day10;
    }


    public String getDay11() {
        return day11;
    }

    public void setDay11(String day11) {
        this.day11 = day11;
    }

    public String getDay12() {
        return day12;
    }

    public void setDay12(String day12) {
        this.day12 = day12;
    }

    public String getDay13() {
        return day13;
    }

    public void setDay13(String day13) {
        this.day13 = day13;
    }

    public String getDay14() {
        return day14;
    }

    public void setDay14(String day14) {
        this.day14 = day14;
    }

    public String getDay15() {
        return day15;
    }

    public void setDay15(String day15) {
        this.day15 = day15;
    }

    public String getDay16() {
        return day16;
    }

    public void setDay16(String day16) {
        this.day16 = day16;
    }

    public String getDay17() {
        return day17;
    }

    public void setDay17(String day17) {
        this.day17 = day17;
    }

    public String getDay18() {
        return day18;
    }

    public void setDay18(String day18) {
        this.day18 = day18;
    }

    public String getDay19() {
        return day19;
    }

    public void setDay19(String day19) {
        this.day19 = day19;
    }

    public String getDay20() {
        return day20;
    }

    public void setDay20(String day20) {
        this.day20 = day20;
    }

    public String getDay21() {
        return day21;
    }

    public void setDay21(String day21) {
        this.day21 = day21;
    }

    public String getDay22() {
        return day22;
    }

    public void setDay22(String day22) {
        this.day22 = day22;
    }

    public String getDay23() {
        return day23;
    }

    public void setDay23(String day23) {
        this.day23 = day23;
    }

    public String getDay24() {
        return day24;
    }

    public void setDay24(String day24) {
        this.day24 = day24;
    }

    public String getDay25() {
        return day25;
    }

    public void setDay25(String day25) {
        this.day25 = day25;
    }

    public String getDay26() {
        return day26;
    }

    public void setDay26(String day26) {
        this.day26 = day26;
    }

    public String getDay27() {
        return day27;
    }

    public void setDay27(String day27) {
        this.day27 = day27;
    }

    public String getDay28() {
        return day28;
    }

    public void setDay28(String day28) {
        this.day28 = day28;
    }

    public String getDay29() {
        return day29;
    }

    public void setDay29(String day29) {
        this.day29 = day29;
    }

    public String getDay30() {
        return day30;
    }

    public void setDay30(String day30) {
        this.day30 = day30;
    }

    public String getDay31() {
        return day31;
    }

    public void setDay31(String day31) {
        this.day31 = day31;
    }

    public static String getSETDAY() {
        return SETDAY;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getHourto() {
        return hourto;
    }

    public void setHourto(int hourto) {
        this.hourto = hourto;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getMinuteto() {
        return minuteto;
    }

    public void setMinuteto(int minuteto) {
        this.minuteto = minuteto;
    }
}
