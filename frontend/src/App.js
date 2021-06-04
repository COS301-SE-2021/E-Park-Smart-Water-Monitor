import './App.css';

import { BrowserRouter, Route, Switch } from 'react-router-dom';

import Home from './views/Home';
import About from './views/About';
// import Contact from './components/Contact';
// import Error from './components/Error';
// import Navigation from './components/Navigation';

const App = () => {
  return (
    // <main>
    //   <div className="App">
    //     <header className="App-header">
    //       <img src={logo} className="App-logo" alt="logo" />
    //       <p>
    //         Edit <code>src/App.js</code> and save to reload.
    //       </p>
    //       <a
    //         className="App-link"
    //         href="https://reactjs.org"
    //         target="_blank"
    //         rel="noopener noreferrer"
    //       >
    //         Learn React
    //       </a>
    //     </header>
    //
    //   </div>
    // </main>

      <BrowserRouter>
        <div>
          <Navigation />
          <Switch>
            <Route path="/" component={Home} exact/>
            <Route path="/about" component={About}/>
          </Switch>
        </div>
      </BrowserRouter>

  );
}

export default App;
