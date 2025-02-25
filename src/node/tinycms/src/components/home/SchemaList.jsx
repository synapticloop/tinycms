export default function SchemaList() {
	return(
			<div className="row my-4">
				<div className="col-lg-12 col-12">
					<div className="custom-block bg-white">
						<h5 className="mb-4">Schema</h5>

						<div className="table-responsive">
							<table className="account-table table">
								<thead>
								<tr>
									<th scope="col">Name</th>
									<th scope="col">Last Published</th>
									<th scope="col"># Items</th>
									<th scope="col">Actions</th>
								</tr>
								</thead>

								<tbody>
								<tr>
									<td scope="row">July 5, 2023</td>

									<td scope="row">10:00 PM</td>

									<td scope="row">Shopping</td>

									<td scope="row">C2C Transfer</td>
								</tr>
								</tbody>
							</table>
						</div>

						<nav aria-label="Page navigation example">
							<ul className="pagination justify-content-center mb-0">
								<li className="page-item">
									<a className="page-link" href="#" aria-label="Previous">
										<span aria-hidden="true">Prev</span>
									</a>
								</li>

								<li className="page-item active" aria-current="page">
									<a className="page-link" href="#">1</a>
								</li>

								<li className="page-item">
									<a className="page-link" href="#">2</a>
								</li>

								<li className="page-item">
									<a className="page-link" href="#">3</a>
								</li>

								<li className="page-item">
									<a className="page-link" href="#">4</a>
								</li>

								<li className="page-item">
									<a className="page-link" href="#" aria-label="Next">
										<span aria-hidden="true">Next</span>
									</a>
								</li>
							</ul>
						</nav>
					</div>
				</div>
			</div>
	);
}