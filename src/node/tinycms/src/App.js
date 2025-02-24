import './css/tinycms.css';
import './css/bootstrap.min.css';
import './css/bootstrap-icons.css';

import Header from "./components/Header";
import NavSideBar from "./components/NavSideBar";

function App() {
  return (
      <div className="App">
        <Header/>
        <div className="container-fluid">
          <div className="row">
            <NavSideBar />
          </div>
        </div>
      </div>
  );
}

export default App;
