import React, { Component } from 'react';
import './style.css';

class AddTicket extends Component {
  render() {
    return (
      <div class="centering">
        <div>
          <p>Skicka din biljett till Etern!</p>
          <input type="text" placeholder="xxxxxxxxxx" />
          <input type="date" />
          <button>Till Etern, och vidare!</button>
        </div>
      </div>
    )
  }
}

export default AddTicket;