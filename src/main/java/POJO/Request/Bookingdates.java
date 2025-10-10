package POJO.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Bookingdates{

	@JsonProperty("checkin")
	private String checkin;

	@JsonProperty("checkout")
	private String checkout;


}