package decorator;

public class FastDecorator extends BaseDecorator{
	
	public FastDecorator(BaseSpirit spirit) {
		super(spirit);
	}
	
	public int run() {
		return mSpirit.run()*2;
	}
}
