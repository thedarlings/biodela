import React, { Component } from 'react';
import './style.css';

class GetTicket extends Component {
  constructor(props) {
    super(props);
    this.state = { numberOfTickets: 3 }
  }

  render() {
    return (
      <div className="horizontalCentering">
        <div>
          <div className="horizontalCentering ">
            {this.state.numberOfTickets}
          </div>
          <button
            onClick={() => this.setState({numberOfTickets: (this.state.numberOfTickets - 1)})}
          >
            Tillkalla Etern
          </button>
        </div>
      </div>
    )
  }
}

export default GetTicket;