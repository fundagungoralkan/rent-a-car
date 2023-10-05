package com.rentacar.entity.concretes.user;

import com.rentacar.entity.abstracts.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="admins")
@SuperBuilder
public class Admin  extends User {

    private boolean built_in;
}
