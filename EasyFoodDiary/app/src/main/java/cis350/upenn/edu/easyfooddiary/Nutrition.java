package cis350.upenn.edu.easyfooddiary;

/**
 * Created by chrisf on 10/26/17.
 */

public class Nutrition {
    protected String calories, carbs, protein, fat;

    public Nutrition(){

    }

    public void setCalories(String s){
        this.calories=s;
    }

    public void setCarbs(String s){
        this.carbs=s;
    }

    public void setProtein(String s){
        this.protein=s;
    }

    public void setFat(String s){
        this.fat=s;
    }

    public String getCalories(){
        return calories;
    }

    public String getCarbs(){
        return carbs;
    }

    public String getProtein(){
        return protein;
    }

    public String getFat(){
        return fat;
    }
}
