import { useEffect, useState } from "react";
import { useLocation, useParams } from "react-router-dom";
import type {Watchable} from "../../types/Watchable.ts";
import {getWatchableById} from "../../api/ApiWatchables.ts";
import WatchableCardDetails from "./WatchableCardDetails.tsx";


type LocationState = { watchable?: Watchable };

export default function WatchableDetailsPage() {
    const { id } = useParams();
    const location = useLocation();
    const state = (location.state as LocationState | null) ?? null;

    // ✅ start with the watchable passed from the preview page (if it exists)
    const [watchable, setWatchable] = useState<Watchable | null>(state?.watchable ?? null);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        // If we already have it, no request needed.
        if (watchable || !id) return;

        // Otherwise (refresh / direct URL), load it
        getWatchableById(id)
            .then((data) => {
                setWatchable(data);
                setError(null);
            })
            .catch((e: unknown) => {
                setError(e instanceof Error ? e.message : "Unknown error");
            });
    }, [id, watchable]);

    if (!id) return <p>Missing id.</p>;
    if (error) return <p style={{ color: "salmon" }}>Error: {error}</p>;
    if (!watchable) return <p>Loading…</p>;

    return (
        <section>
            <WatchableCardDetails watchable={watchable} />
        </section>
    );
}