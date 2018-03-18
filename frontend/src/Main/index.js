import React, { Component } from 'react';
import './style.css';
import AddTicket from '../AddTicket';
import GetTicket from '../GetTicket';
import TicketList from '../TicketList';

class Main extends Component {
  constructor(props) {
    super(props);
    this.getMyTickets = this.getMyTickets.bind(this);
    this.state = { tickets: [] };
  }

  getMyTickets() {
    fetch("http://192.168.0.9:8080/api/tickets?sessiontoken=" + localStorage.sessiontoken, {
      method: "GET",
      mode: 'cors',
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    })
    .then(response => {
      if (response.status === 200) {
        response.json().then(data => {
          this.setState({ tickets: data });
        })
      }
    })
  }

  componentDidMount() {
    this.getMyTickets();
  }

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
        <div className="box get_ticket"><GetTicket getMyTickets={this.getMyTickets} /></div>
        <div className="box ticket_list"><TicketList tickets={this.state.tickets} /></div>
      </div>
    )
  }
}

export default Main;