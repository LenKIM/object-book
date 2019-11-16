package l3연결되는합성객체;

import java.util.HashSet;
import java.util.Set;

public class Plan {

    private Calculator calc;
    private Set<Call> calls = new HashSet<>();

    public final void addCall(Call call){
        calls.add(call);
    }

    public final void setCalculator(Calculator calculator){
        this.calc = calculator;
    }

    public final Money calculateFee(){
        return  calc.calcCallFee(calls, Money.ZERO);
    }
}
