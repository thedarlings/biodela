import React, { Component } from 'react';
import { connect } from 'react-redux';
import './style.css';
import AddTicket from '../AddTicket';
import GetTicket from '../GetTicket';
import TicketList from '../TicketList';
import { receivedTicket } from '../../actions/ticketActions';

class Main extends Component {
  constructor(props) {
    super(props);
    this.getMyTickets = this.getMyTickets.bind(this);
    //this.state = { tickets: [] };
  }

  getMyTickets() {
    fetch("/api/tickets?sessiontoken=" + localStorage.sessiontoken, {
      method: "GET",
      mode: 'cors',
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    })
    .then(response => {
      if (response.status === 200) {
        response.json().then(data => {
          this.props.dispatch(receivedTicket(data));
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
        <div className="box ticket_list"><TicketList tickets={this.props.tickets} /></div>
      </div>
    )
  }
}


const mapStateToProps = state => ({
  tickets: state.tickets
})

export default connect(mapStateToProps)(Main);