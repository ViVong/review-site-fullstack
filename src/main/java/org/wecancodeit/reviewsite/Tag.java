package org.wecancodeit.reviewsite;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Tag {
	
	@Id
	@GeneratedValue
	private long id;
	private String name;

	@ManyToMany(mappedBy = "categories")
	private Collection<Review> reviews;
	
	protected Tag() {}
	
	public Tag(String name, Review...reviews) {
		this.name = name;
		this.reviews = new HashSet<>(Arrays.asList(reviews));
	}
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public Collection<Review> getReviews() {
		return reviews;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tag other = (Tag) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
