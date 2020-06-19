package de.ihrigb.bachelorparty.server.web;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import de.ihrigb.bachelorparty.server.persistence.model.Tracker;
import de.ihrigb.bachelorparty.server.persistence.repository.TrackerRepository;
import de.ihrigb.commons.Assert;
import de.ihrigb.commons.LatLng;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RestController
@RequestMapping("/trackers")
@RequiredArgsConstructor
public class TrackerController {

	private final TrackerRepository trackerRepository;

	@PostMapping
	public ResponseEntity<?> createTracker(@RequestBody CreateTracker createTracker) {
		Tracker t = trackerRepository.save(createTracker.getJpaBean());
		URI location = MvcUriComponentsBuilder.fromMethodName(TrackerController.class, "getTracker", t.getId()).build()
				.toUri();
		return ResponseEntity.created(location).body(new GetTracker(t));
	}

	@GetMapping("/{id}")
	public ResponseEntity<GetTracker> getTracker(@PathVariable("id") String id) {
		return ResponseEntity.of(this.trackerRepository.findById(id).map(GetTracker::new));
	}

	@GetMapping("/latest")
	public ResponseEntity<GetTracker> getLatest() {
		return ResponseEntity.of(this.trackerRepository.findLatest().map(GetTracker::new));
	}

	@GetMapping
	public ResponseEntity<List<GetTracker>> getPath() {
		return ResponseEntity.ok(this.trackerRepository.getPath().stream().map(GetTracker::new).collect(Collectors.toList()));
	}

	@Getter
	static class GetTracker {
		private String id;
		private Instant time;
		private double lat;
		private double lng;

		GetTracker(Tracker t) {
			Assert.notNull(t, "tracker must not be null.");
			this.id = t.getId();
			this.time = t.getTime();
			this.lat = t.getLatLng().getLatitude();
			this.lng = t.getLatLng().getLongitude();
		}
	}

	@Setter
	static class CreateTracker {

		private double lat;
		private double lng;

		@JsonIgnore
		Tracker getJpaBean() {
			Tracker t = new Tracker();
			t.setLatLng(new LatLng(lat, lng));
			t.setTime(Instant.now());
			return t;
		}
	}
}
