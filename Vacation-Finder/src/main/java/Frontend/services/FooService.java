package Frontend.services;

import org.springframework.stereotype.Service;

/**
 * Created by jlutteringer on 1/21/16.
 */
@Service
public class FooService {
	public String foo(String value1, String value2) {
		return value1 + " " + value2;
	}
}