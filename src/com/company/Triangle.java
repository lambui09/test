package com.company;

public class Triangle {
	public double a,b,c;
	public Triangle(double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public Triangle(){
        a=0; b=0;c=0;
    }
    public int isTriangle(){
        boolean istriangle =false;
        if(a<b+c && b<a+c && c<a+b)
            istriangle = true;
        if(istriangle){
            if(a==b && b==c)
                return 2; //tam gíac đều
            else if(a!=b && b!=c && a!=c)
                return 0; //tam giác thường
            else
                return 1; // tam giác cân
        }
        else return -1;
    }
}
