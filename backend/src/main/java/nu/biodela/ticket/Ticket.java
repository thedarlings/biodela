package nu.biodela.ticket;

import java.util.Date;

public class Ticket {
  private long ticketId;
  private long code;
  private Date expiryDate;
  private Date createdAt;
  private String provider;
  private String ownerId;
  private boolean used;

  public Ticket(long ticketId, long code, Date expiryDate, Date createdAt, String provider,
      String ownerId, boolean used) {
    this.ticketId = ticketId;
    this.code = code;
    this.expiryDate = expiryDate;
    this.createdAt = createdAt;
    this.provider = provider;
    this.ownerId = ownerId;
    this.used = used;
  }

  public Ticket(long code, Date expireDate, Date createdAt, String provider, String ownerId,
      boolean used) {
    this.code = code;
    this.expiryDate = expireDate;
    this.createdAt = createdAt;
    this.provider = provider;
    this.ownerId = ownerId;
    this.used = used;
  }


}
