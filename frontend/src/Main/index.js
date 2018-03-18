import React, { Component } from 'react';
import './style.css';
import AddTicket from '../AddTicket';
import GetTicket from '../GetTicket';
import TicketList from '../TicketList';

class Main extends Component {
  render() {
    return (
      <div className="grid">
        <button
          onClick={() => this.props.logout()}
          className="logout"
        >
          Logga ut
        </button>
        <div className="box add_ticket">
          <AddTicket />
        </div>
        <div className="box get_ticket"><GetTicket /></div>
        <div className="box ticket_list"><TicketList /></div>
      </div>
    )
  }
}

export default Main;