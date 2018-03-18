package nu.biodela.ticket;

import java.util.Date;
import java.util.Optional;

public class Ticket {
  private long ticketId;
  private String code;
  private Date expiryDate;
  private Date createdAt;
  private Long provider;
  private Long ownerId;
  private boolean used;

  public Ticket(long ticketId, String code, Date expiryDate, Date createdAt, long provider,
      long ownerId, boolean used) {
    this.ticketId = ticketId;
    this.code = code;
    this.expiryDate = expiryDate;
    this.createdAt = createdAt;
    this.provider = provider;
    this.ownerId = ownerId;
    this.used = used;
  }

  public Ticket(String code, Date expireDate, Date createdAt, long provider, long ownerId,
      boolean used) {
    this.code = code;
    this.expiryDate = expireDate;
    this.createdAt = createdAt;
    this.provider = provider;
    this.ownerId = ownerId;
    this.used = used;
  }


  public Optional<Long> getOwnerId() {
    return Optional.ofNullable(ownerId);
  }

  public boolean isUnClaimned() {
    return ownerId == null;
  }

  public int compareExpirationDate(Ticket other) {
    return expiryDate.compareTo(other.expiryDate);
  }

  public void setOwner(Long owner) {
    this.ownerId = owner;
  }
}
