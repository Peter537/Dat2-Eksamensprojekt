linear_extrude(height = 97.0, twist = 0.0, scale = 1.0, slices = 1, center = true)
{
    scale([195.0, 45.0])
    {
        M52();
    }
}

module M52()
{
    polygon
    (
        points =
        [
            [-0.5, -0.5], 
            [0.5, -0.5], 
            [0.5, 0.5], 
            [-0.5, 0.5]
        ],
        paths =
        [
            [0, 1, 2, 3]
        ]
    );
}
