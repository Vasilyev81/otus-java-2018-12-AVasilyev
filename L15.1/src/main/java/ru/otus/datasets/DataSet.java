package ru.otus.datasets;

import lombok.*;

import javax.persistence.*;


@NoArgsConstructor
@MappedSuperclass
public class DataSet {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
}
