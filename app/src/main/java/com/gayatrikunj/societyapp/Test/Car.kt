package com.gayatrikunj.societyapp.Test

import android.view.View

open class Car() {
    var price:Int?=null
    var color:String?=null

    constructor(price:Int,color:String):this(){
        this.price = price
        this.color = color
    }

    fun getBrandPrice():Int{
        return 3400
    }
    fun execute(data:Int,op:Operation)= when (op){
        is Operation.add->data+op.data
        is Operation.sub->data+op.data
    }

}
sealed class Operation{
    class add(val data:Int):Operation()
    class sub(val data:Int):Operation()
}
sealed class UiOp {
    object Show: UiOp()
    object Hide: UiOp()
    class TranslateX(val px: Float): UiOp()
    class TranslateY(val px: Float): UiOp()
}
fun execute(view: View, op: UiOp) = when (op) {
    UiOp.Show -> view.visibility = View.VISIBLE
    UiOp.Hide -> view.visibility = View.GONE
    is UiOp.TranslateX -> view.translationX = op.px
    is UiOp.TranslateY -> view.translationY = op.px
}