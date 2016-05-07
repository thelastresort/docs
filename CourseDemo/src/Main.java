import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Main {

	public static final void main(String[] args){
		int MAX = 1000*1000*1000;
		List<Integer> list = new ArrayList<Integer>();
		for (int i=0; i<MAX; ++i) {
			System.out.println(i);
			Integer k = new Integer(i);
			//list.add(k);
			
			/*
			try{
				Thread.sleep(1);
			}catch(Exception e) {
	
			}
			*/
		}
	}


	public static final void main1(String[] args){
		System.out.println("main begin");

		Name name = new Name();
		name.firstName = "first";

		modifyName(name);
		System.out.println(name.firstName);
	
		ArrayList<Name> list = new ArrayList<Name>();
		list.add(new Name());
		list.add(new Name());
		modifyList(list);
		System.out.println("list size is " + list.size());
	}


	public List<Name> getUsers() {
		ArrayList<Name> list = new ArrayList<Name>();

		// TODO ...

		return list;
	}

	
	private static void modifyList(List<Name> list) {
		if (null != list) {
			list.remove(0);
		}
	}
	
	
	private static void modifyName(Name name) {
		if (null != name) {
			name.firstName = "modify";
		}
	}


	public static class Name {
		String firstName;
	}
}
