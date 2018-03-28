import React, { Component } from 'react';
import './style.css';

class GetTicket extends Component {
  constructor(props) {
    super(props);
    this.state = { numberOfTickets: 0 }
  }

  componentDidMount() {
    this.getNumberOfTickets();
  }

  getNumberOfTickets() {
    fetch('/api/tickets/available?sessiontoken=' + localStorage.sessiontoken, {
      method: 'GET',
      mode: 'cors',
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    })
    .then(response => {
      return response.text();
    })
    .then(data => {
      this.setState({ numberOfTickets: parseInt(data, 10) });
    })
  }

  getTicket() {
    fetch('/api/tickets/claim?sessiontoken=' + localStorage.sessiontoken, {
      method: 'GET',
      mode: 'cors',
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    })
    .then(response => {
      return response.json();
    })
    .then(data => {
      this.props.getMyTickets()
      this.getNumberOfTickets();
    })
  }

  render() {
    return (
      <div className="horizontalCentering">
        <div>
          <div className="horizontalCentering" style={{ fontSize: "7em" }}>
            {this.state.numberOfTickets}
          </div>
          <button
            disabled={this.state.numberOfTickets <= 0}
            onClick={() => this.getTicket()}
          >
            Tillkalla Etern
          </button>
        </div>
      </div>
    )
  }
}

export default GetTicket;