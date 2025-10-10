package POJO.Request.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateBookingRes{

	@JsonProperty("booking")
	private Booking booking;

	@JsonProperty("bookingid")
	private int bookingId;


}