package de.ihrigb.bachelorparty.server.persistence.converter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import de.ihrigb.commons.LatLng;

@Converter(autoApply = true)
public class LatLngConverter implements AttributeConverter<LatLng, String> {

	private final DecimalFormat df = new DecimalFormat("#.######", new DecimalFormatSymbols(Locale.US));

	@Override
	public String convertToDatabaseColumn(final LatLng attribute) {
		if (attribute == null) {
			return null;
		}

		return String.format("%s;%s", df.format(attribute.getLatitude()), df.format(attribute.getLongitude()));
	}

	@Override
	public LatLng convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}

		String[] split = dbData.split(";");
		if (split.length != 2) {
			throw new RuntimeException("Bad formatting of LatLng.");
		}

		try {
			double lat = df.parse(split[0]).doubleValue();
			double lng = df.parse(split[1]).doubleValue();
			return new LatLng(lat, lng);
		} catch (ParseException e) {
			throw new RuntimeException("Exception while parsing LatLng", e);
		}
	}
}