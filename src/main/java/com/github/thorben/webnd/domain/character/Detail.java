package com.github.thorben.webnd.domain.character;

import com.github.thorben.webnd.foundation.AbstractEntity;

import javax.persistence.*;

@Entity(name = "Details")
@Table(name = "details")
public class Detail extends AbstractEntity<Integer> {

	@Id
	@GeneratedValue
	private Integer id;
	@JoinColumn(name = "id", table = "detail_types")
	private DetailType type;
	@Column(name = "detail_value")
	private String value;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "character_id")
	private Character character;

	protected Detail() {
		// JPA
	}

	@Override
	public Integer getId() {
		return id;
	}
}
