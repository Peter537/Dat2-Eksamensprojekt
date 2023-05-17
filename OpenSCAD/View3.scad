linear_extrude(height = 45.0, twist = 0.0, scale = 1.0, slices = 1, center = false)
{
    scale([3000.0, 195.0])
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
