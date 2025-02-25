import '../components/Header';
import '../components/NavSideBar';
import '../components/HomeBody';
import Header from "../components/Header";
import NavSideBar from "../components/NavSideBar";
import HomeBody from "../components/HomeBody";
import Footer from "../components/Footer";
import CollectionBody from "../components/CollectionBody";

export default function Collection() {
	return (
			<div className="App">
				<Header/>
				<div className="container-fluid">
					<div className="row">
						<NavSideBar/>
						<CollectionBody/>
					</div>
				</div>
			</div>
);
}
