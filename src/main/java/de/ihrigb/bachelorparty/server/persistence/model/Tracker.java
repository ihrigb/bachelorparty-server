package de.ihrigb.bachelorparty.server.persistence.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import de.ihrigb.commons.LatLng;
import lombok.Data;

@Data
@Entity
@Table(name = "trackers")
public class Tracker {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "id")
	private String id;

	@Column(name = "time")
	private Instant time;

	@Column(name = "latlng")
	private LatLng latLng;
}
