import { useLocation } from 'react-router-dom'

export default function NavSideBar() {
	const location = useLocation();
	const { hash, pathname, search } = location;

	return (
			<nav id="sidebarMenu" className="col-md-3 col-lg-3 d-md-block sidebar collapse">
				<div className="position-sticky py-4 px-3 sidebar-sticky">
					<ul className="nav flex-column h-100">
						<li className="nav-item">
							<a className={"nav-link " + (pathname === "/" ? 'active' : '')} aria-current="page" href="/">
								<i className="bi-house-fill me-2"></i>
								Overview
							</a>
						</li>

						<li className="nav-item">
							<a className={"nav-link " + (pathname.startsWith("/collection") ? 'active' : '')} aria-current="page" href="/collection/">
								<i className="bi-house-fill me-2"></i>
								Collections
							</a>
						</li>

						<li className="nav-item">
							<a className="nav-link" href="/data">
								<i className="bi-wallet me-2"></i>
								Data
							</a>
						</li>

						<li className="nav-item">
							<a className="nav-link" href="/schema">
								<i className="bi-gear me-2"></i>
								Schema
							</a>
						</li>

					</ul>
				</div>
			</nav>

	);
}
