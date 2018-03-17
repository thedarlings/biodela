import React, { Component } from 'react';
import './style.css';

class TicketList extends Component {
  constructor(props) {
    super(props);
    this.state = { tickets: [{code: "0987654321", date: "27/4-19"}, {code: "1234567890", date: "27/4-29"}] }
  }

  render() {
    return (
      <div class="horizontalCentering">
        <div>
          <ul>
            {this.state.tickets.map(ticket => {
              return <li>{ticket.code}</li>
            })}
          </ul>
        </div>
      </div>
    )
  }
}

export default TicketList;