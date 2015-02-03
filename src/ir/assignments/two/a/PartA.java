// Leon Thai (#55997869)

package ir.assignments.two.a;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* This class is for testing only*/

public class PartA {

	public static void main(String[] args) throws IOException {
		ArrayList<String> test = Utilities.tokenizeFile(new File("ty.txt"));
		System.out.println(test);
		System.out.println("");
		
		
		Frequency a = new Frequency("hello there");
		a.incrementFrequency();
		
		Frequency b = new Frequency("my name");
		b.incrementFrequency();
		
		Frequency c = new Frequency("is Blah");
		c.incrementFrequency();
		
		Frequency d = new Frequency("blah blah");
		d.incrementFrequency();
		d.incrementFrequency();
		
		List<Frequency> f = new ArrayList<Frequency>();
		f.add(a);
		f.add(b);
		f.add(c);
		f.add(d);
		
		Utilities.printFrequencies(f);
		

	}

}
