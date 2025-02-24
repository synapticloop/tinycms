export default function Header() {
	return (
		<header className="navbar sticky-top flex-md-nowrap">
			<div className="col-md-3 col-lg-3 me-0 px-3 fs-6">
				<a className="navbar-brand" href="/"><i className="bi-box"></i>TinyCMS</a>
			</div>
			<button className="navbar-toggler position-absolute d-md-none collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#sidebarMenu" aria-controls="sidebarMenu"  aria-expanded="false" aria-label="Toggle navigation">
				<span className="navbar-toggler-icon"></span>
			</button>
		</header>
	);
}