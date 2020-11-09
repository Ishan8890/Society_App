package com.gayatrikunj.societyapp.Test

class Truck:Car {
    var name: String? = null
    var mileage: Double? = null

    constructor(price:Int,color:String,name: String, mileage: Double,temperature:String):super(price,color){

    }

    fun getCalledCar(){
        var car=Car()
        car.getCalculation()

        var dataList = arrayListOf<Int>()
        dataList.add(12)
        dataList.add(4)
        dataList.add(1)
        dataList.add(80)
    }


}
fun ArrayList<Int>.getMax():Int{
    var max:Int = 0
    for(items in this){
        if(items>max){
            max = items
        }
    }
    return max
}

fun Car.getCalculation():Int{
    return this.getBrandPrice()*10+80
}