abstract class BasicRatePolicy {

    def calculateFee(phone: Phone): Money = 
        phone.calls.map(calculateCallFee(_)).reduce(_ + _)

    protected def calculateCallFee(call: Call): Money;
}


class RegularPolicy(val amount: Money, val seconds: Duration) extends BasicRatePolicy {
    
    override protected def calculateCallFee(call: Call): Money = 
        amount * (call.duration.getSeconds / seconds.getSeconds)
}

class NightlyDiscountPolicy(val amount: Money, val seconds: Duration) extends BasicRatePolicy {
    
    override protected def calculateCallFee(call: Call): Money = 
        amount * (call.duration.getSeconds / seconds.getSeconds) - 30;  //임의의 코드
}