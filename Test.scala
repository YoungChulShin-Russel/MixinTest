
////////////////////////////////////////////////////////////////
// 기본 정책
////////////////////////////////////////////////////////////////
abstract class BasicRatePolicy {

    def calculateFee(phone: Phone): Money = 
        phone.calls.map(calculateCallFee(_)).reduce(_ + _)

    protected def calculateCallFee(call: Call): Money;
}

////////////////////////////////////////////////////////////////
// 기본 요금 할인 정책
////////////////////////////////////////////////////////////////
class RegularPolicy(val amount: Money, val seconds: Duration) extends BasicRatePolicy {
    
    override protected def calculateCallFee(call: Call): Money = 
        amount * (call.duration.getSeconds / seconds.getSeconds)
}

class NightlyDiscountPolicy(val amount: Money, val seconds: Duration) extends BasicRatePolicy {
    
    override protected def calculateCallFee(call: Call): Money = 
        amount * (call.duration.getSeconds / seconds.getSeconds) - 30;  //임의의 코드
}

////////////////////////////////////////////////////////////////
// 부가 정책
////////////////////////////////////////////////////////////////
trait TaxablePolicy extends BasicRatePolicy {
    def taxRate: Double

    override def calculateFee(phone: Phone): Money = {
        var fee = super.calculateFee(phone)
        return fee + fee * taxRate
    }
}
