import Footer from "./Footer";
import CollectionList from "./home/CollectionList";
import DataList from "./home/DataList";
import SchemaList from "./home/SchemaList";

export default function CollectionBody() {
	return (
			<main className="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
				<div className="title-group mb-3">
					<h1 className="h2 mb-0"><em>[TinyCMS]</em> Collections</h1>
				</div>

				<CollectionList />

				<Footer />
			</main>

	);
}
