/**
 * 
 */
package tr.edu.ankara.blm489.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author sskl
 *
 */
@Entity
@DiscriminatorValue("Admin")
public class Admin extends User {

}
