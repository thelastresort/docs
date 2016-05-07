package decorator;

public abstract class BaseDecorator extends BaseSpirit{
	
	protected BaseSpirit mSpirit;
	
	public BaseDecorator(BaseSpirit spirit) {
		this.mSpirit = spirit;
	}
	
	public int run() {
		return mSpirit.run();
	}
}
