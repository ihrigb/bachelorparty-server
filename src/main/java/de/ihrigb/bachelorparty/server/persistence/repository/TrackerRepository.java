package de.ihrigb.bachelorparty.server.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.ihrigb.bachelorparty.server.persistence.model.Tracker;

public interface TrackerRepository extends JpaRepository<Tracker, String> {

	default Optional<Tracker> findLatest() {
		return this.findFirstByOrderByTimeDesc();
	}

	Optional<Tracker> findFirstByOrderByTimeDesc();

	default List<Tracker> getPath() {
		return this.findByOrderByTimeAsc();
	}

	List<Tracker> findByOrderByTimeAsc();
}
